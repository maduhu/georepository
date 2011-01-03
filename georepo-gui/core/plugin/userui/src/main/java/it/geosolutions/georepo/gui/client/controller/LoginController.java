/*
 * $Header: it.geosolutions.georepo.gui.client.controller.LoginController,v. 0.1 08/lug/2010 10.40.05 created by frank $
 * $Revision: 0.1 $
 * $Date: 08/lug/2010 10.40.05 $
 *
 * ====================================================================
 *
 * Copyright (C) 2010 GeoSolutions S.A.S.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. 
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.georepo.gui.client.controller;

import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.model.AOI;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.client.service.LoginRemote;
import it.geosolutions.georepo.gui.client.service.LoginRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.AOIUserPrefContainer;
import it.geosolutions.georepo.gui.client.widget.AddUserWidget;
import it.geosolutions.georepo.gui.client.widget.ChooseUserPagWidget;
import it.geosolutions.georepo.gui.client.widget.DGWATCHChooserWidget;
import it.geosolutions.georepo.gui.client.widget.DGWATCHSearchWidget;
import it.geosolutions.georepo.gui.client.widget.DGWATCHUpdateWidget;
import it.geosolutions.georepo.gui.client.widget.LoginStatus.EnumLoginStatus;
import it.geosolutions.georepo.gui.client.widget.LoginWidget;
import it.geosolutions.georepo.gui.client.widget.SearchPagUserWidget;
import it.geosolutions.georepo.gui.client.widget.SearchStatus.EnumSearchStatus;
import it.geosolutions.georepo.gui.client.widget.UpdateUserWidget;
import it.geosolutions.georepo.gui.client.widget.UserManagementWidget;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author frank
 * 
 */
public class LoginController extends Controller {

	private LoginRemoteAsync loginRemote = LoginRemote.Util.getInstance();
	private LoginWidget loginWidget;
	private DGWATCHChooserWidget<User> chooseUserWidget;
	private UserManagementWidget userManagementWidget;
	private AddUserWidget addUserWidget;
	private DGWATCHSearchWidget<User> searchWidget;
	private DGWATCHUpdateWidget<User> updateUser;
	private AOIUserPrefContainer aoiUserContainer;
	
	/**
	 * LoginController
	 */
	public LoginController() {
		registerEventTypes(
				DGWATCHEvents.INIT_USER_UI_MODULE,// DGWATCHEvents.INIT,
				DGWATCHEvents.INIT_DGWATCH_WIDGET, DGWATCHEvents.LOGIN,
				DGWATCHEvents.ATTACH_USER_WIDGET,
				DGWATCHEvents.SHOW_CHOOSER_USER_WIDGET,
				DGWATCHEvents.SHOW_ADD_USER_WIDGET, DGWATCHEvents.SAVE_USER,
				DGWATCHEvents.SHOW_SEARCH_USER_WIDGET,
				DGWATCHEvents.BIND_SELECTED_USER, DGWATCHEvents.DELETE_USER,
				DGWATCHEvents.SHOW_UPDATE_USER_WIDGET,
				DGWATCHEvents.UPDATE_USER, DGWATCHEvents.NOTIFY_UNSHARE_ERROR,
				DGWATCHEvents.NOTIFY_UNSHARE_SUCCESS,
				DGWATCHEvents.UNBIND_USER_WIDGET, DGWATCHEvents.LOGOUT,
				DGWATCHEvents.CHECK_RELATED_USERS_COUNT);
	}

	@Override
    protected void initialize() {
		this.loginWidget = new LoginWidget();

		this.loginWidget.addListener(Events.Hide, new Listener<WindowEvent>() {

			public void handleEvent(WindowEvent be) {
				Dispatcher.forwardEvent(DGWATCHEvents.INIT_DGWATCH_MAIN_UI);
				loginWidget.reset();
			}
		});

		initWidget();
	}

	/**
	 * Init All Controller Widgets
	 */
	private void initWidget() {
		this.chooseUserWidget = new ChooseUserPagWidget(loginRemote);
		this.searchWidget = new SearchPagUserWidget(this.loginRemote);
		this.addUserWidget = new AddUserWidget();
		this.updateUser = new UpdateUserWidget();
		this.aoiUserContainer = new AOIUserPrefContainer(this.loginRemote);
	}

	@Override
	public void handleEvent(AppEvent event) {
		if (event.getType() == DGWATCHEvents.INIT_DGWATCH_WIDGET)
			onShowLoginWidget();

		if (event.getType() == DGWATCHEvents.LOGIN)
			onLogin(event);

		if (event.getType() == DGWATCHEvents.ATTACH_USER_WIDGET)
			onAttachUserWidget(event);

		if (event.getType() == DGWATCHEvents.SHOW_CHOOSER_USER_WIDGET)
			onShowChooseUserWidget();

		if (event.getType() == DGWATCHEvents.SHOW_ADD_USER_WIDGET)
			onShowAddUserWidget();

		if (event.getType() == DGWATCHEvents.SAVE_USER)
			onSaveUser(event);

		if (event.getType() == DGWATCHEvents.SHOW_SEARCH_USER_WIDGET)
			onShowSearchUSerWidget();

		if (event.getType() == DGWATCHEvents.BIND_SELECTED_USER)
			onBindSelectedUser(event);

		if (event.getType() == DGWATCHEvents.DELETE_USER)
			onDeleteUser(event);

		if (event.getType() == DGWATCHEvents.SHOW_UPDATE_USER_WIDGET)
			onShowUpdateUserWidget(event);

		if (event.getType() == DGWATCHEvents.UPDATE_USER)
			onUpdateUser(event);

		if (event.getType() == DGWATCHEvents.NOTIFY_UNSHARE_ERROR)
			onNotifyUnshareError();

		if (event.getType() == DGWATCHEvents.NOTIFY_UNSHARE_SUCCESS)
			onNotifyUnshareSuccess();

		if (event.getType() == DGWATCHEvents.UNBIND_USER_WIDGET)
			onUnbindUserWidget();

		if (event.getType() == DGWATCHEvents.LOGOUT)
			onLogout();

        if (event.getType() == DGWATCHEvents.CHECK_RELATED_USERS_COUNT)
            onCheckRelatedUsersCount(event);
	}

	private void onCheckRelatedUsersCount(AppEvent event) {
		final AOI aoi = (AOI) event.getData();

       // TODO REFACTOR GG
//		this.loginRemote.getRelatedUsersCount(aoi.getId(),
//				new AsyncCallback<Integer>() {
//
//					public void onFailure(Throwable caught) {
//						Dispatcher
//								.forwardEvent(
//										DGWATCHEvents.SEND_ERROR_MESSAGE,
//										new String[] { "AOI User Preferences",
//												"There was an error loading the data." });
//					}
//
//					public void onSuccess(Integer result) {
//						int count = result.intValue();
//						if (count > 0) {
//							MessageBox
//									.confirm(
//											"AOI User Preferences",
//											"There are users who have preferences on AOI. Do you want to see them ?",
//											new Listener<MessageBoxEvent>() {
//
//												public void handleEvent(
//														MessageBoxEvent be) {
//													aoiUserContainer
//															.getAoiUserPref()
//															.setAoi(aoi);
//													aoiUserContainer.show();
//												}
//											});
//						} else
//							Dispatcher.forwardEvent(DGWATCHEvents.DELETE_AOI,
//									aoi);
//					}
//				});
	}

	private void onLogout() {
		this.loginRemote.logout(new AsyncCallback<Object>() {

			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE,
						new String[] { "Logout Service",
								"There was an error in logout" });
			}

			public void onSuccess(Object result) {
				Dispatcher.forwardEvent(DGWATCHEvents.SESSION_EXPIRED);
			}
		});

	}

	private void onUnbindUserWidget() {
		userManagementWidget.getUserInfo().unBindModel();
	}

	private void onNotifyUnshareSuccess() {
		this.chooseUserWidget.setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
				EnumSearchStatus.STATUS_MESSAGE_AOI_UNSHARE);
		this.chooseUserWidget.getChooser().hide();
	}

	private void onNotifyUnshareError() {
		this.chooseUserWidget.setSearchStatus(
				EnumSearchStatus.STATUS_SEARCH_ERROR,
				EnumSearchStatus.STATUS_MESSAGE_AOI_UNSHARE_ERROR);
	}

	/**
	 * 
	 * @param event
	 */
	private void onUpdateUser(AppEvent event) {
		final User user = (User) event.getData();

        // TODO REFACTOR GG
//		this.loginRemote.updateUser(user, new AsyncCallback<User>() {
//
//			public void onFailure(Throwable caught) {
//				updateUser.setSaveStatus(EnumSaveStatus.STATUS_SAVE_ERROR,
//						EnumSaveStatus.STATUS_MESSAGE_SAVE_ERROR);
//				Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE,
//						new String[] { "Update User",
//								"An error occurred when updating the user " });
//			}
//
//			@SuppressWarnings("deprecation")
//			public void onSuccess(User result) {
//				updateUser.setSaveStatus(EnumSaveStatus.STATUS_SAVE,
//						EnumSaveStatus.STATUS_MESSAGE_SAVE);
//				bindUser(result);
//				Dispatcher.forwardEvent(DGWATCHEvents.SEND_INFO_MESSAGE,
//						new String[] { "Update User",
//								"he user has been successfully updated." });
//				updateUser.close();
//			}
//		});
	}

	/**
	 * 
	 * @param event
	 */
	private void onShowUpdateUserWidget(AppEvent event) {
		this.updateUser.bind((User) event.getData());
		this.updateUser.show();
	}

	/**
	 * 
	 * @param event
	 */
	private void onDeleteUser(AppEvent event) {
		final User user = (User) event.getData();
                // TODO REFACTOR GG
//		this.loginRemote.deleteUser(user.getId(), new AsyncCallback<Object>() {
//
//			public void onFailure(Throwable caught) {
//				Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE,
//						new String[] { "Delete User",
//								"There was an error when deleting user" });
//			}
//
//			public void onSuccess(Object result) {
//				Dispatcher.forwardEvent(
//						DGWATCHEvents.SEND_INFO_MESSAGE,
//						new String[] {
//								"Delete User",
//								"The user with User Name: "
//										+ user.getUserName()
//										+ " was successfully deleted." });
//				userManagementWidget.getUserInfo().unBindModel();
//				Dispatcher.forwardEvent(DGWATCHEvents.RESET_AOI_GRID);
//				Dispatcher.forwardEvent(DGWATCHEvents.RESET_RSS_GRID);
//				Dispatcher.forwardEvent(DGWATCHEvents.USER_SELECTED, null);
//				Dispatcher.forwardEvent(DGWATCHEvents.CHECK_AOI_OWNER, user);
//			}
//		});
	}

	/**
	 * 
	 * @param event
	 */
	private void onBindSelectedUser(AppEvent event) {
		final User user = (User) event.getData();
        // TODO REFACTOR GG
//		this.loginRemote.getUserDetail(user, new AsyncCallback<User>() {
//
//			public void onFailure(Throwable caught) {
//				searchWidget.setSearchStatus(
//						EnumSearchStatus.STATUS_SEARCH_ERROR,
//						EnumSearchStatus.STATUS_MESSAGE_USER_DETAIL_ERROR);
//			}
//
//			public void onSuccess(User result) {
//				searchWidget.setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
//						EnumSearchStatus.STATUS_MESSAGE_USER_DETAIL);
//				searchWidget.cancel();
//				bindUser(result);
//			}
//		});

		// bindUser((User) event.getData());
	}

	/**
	 * 
	 */
	private void onShowSearchUSerWidget() {
		this.searchWidget.show();
	}

	/**
	 * 
	 * @param event
	 */
	private void onSaveUser(AppEvent event) {
        // TODO REFACTOR GG
//		this.loginRemote.saveUser((User) event.getData(),
//				new AsyncCallback<User>() {
//
//					public void onFailure(Throwable caught) {
//						addUserWidget.setSaveStatus(
//								EnumSaveStatus.STATUS_SAVE_ERROR,
//								EnumSaveStatus.STATUS_MESSAGE_SAVE_ERROR);
//						Dispatcher
//								.forwardEvent(
//										DGWATCHEvents.SEND_ERROR_MESSAGE,
//										new String[] { "Save User",
//												"An error occurred while saving the user." });
//					}
//
//					@SuppressWarnings("deprecation")
//					public void onSuccess(User result) {
//						addUserWidget.setSaveStatus(EnumSaveStatus.STATUS_SAVE,
//								EnumSaveStatus.STATUS_MESSAGE_SAVE);
//						bindUser(result);
//						Dispatcher.forwardEvent(
//								DGWATCHEvents.SEND_INFO_MESSAGE, new String[] {
//										"Save User",
//										"the user has been successfully saved with ID : "
//												+ result.getId() });
//						addUserWidget.close();
//					}
//
//				});

	}

	private void onShowAddUserWidget() {
                // TODO REFACTOR GG
//		this.loginRemote.findUserNames(new AsyncCallback<List<RegUser>>() {
//
//			public void onFailure(Throwable caught) {
//				try {
//					throw caught;
//				} catch (ApplicationException e) {
//					MessageBox.alert("Session Expired",
//							"The Session Expired. You must re-login.",
//							new Listener<MessageBoxEvent>() {
//
//								public void handleEvent(MessageBoxEvent be) {
//									Dispatcher
//											.forwardEvent(DGWATCHEvents.SESSION_EXPIRED);
//								}
//							});
//				} catch (Throwable e) {
//					Dispatcher.forwardEvent(DGWATCHEvents.SEND_ERROR_MESSAGE,
//							new String[] { "Member Service",
//									"There was a problem loading data" });
//				}
//
//			}
//
//			public void onSuccess(List<RegUser> result) {
//				addUserWidget.fillStoreRegUser(result);
//				addUserWidget.show();
//			}
//		});

	}

	private void onShowChooseUserWidget() {
		this.chooseUserWidget.getChooser().show();
	}

	private void onAttachUserWidget(AppEvent event) {
		ContentPanel east = (ContentPanel) event.getData();
		this.userManagementWidget = new UserManagementWidget();
		east.add(userManagementWidget);
		east.layout();
	}

	/**
	 * 
	 * @param event
	 */
	private void onLogin(AppEvent event) {
		String[] values = (String[]) event.getData();
		this.loginRemote.authenticate(values[0], values[1],
				new AsyncCallback<User>() {

					public void onSuccess(User user) {
						if (user != null) {
							loginWidget.setStatusLoginFinder(
									EnumLoginStatus.STATUS_LOGIN,
									EnumLoginStatus.STATUS_MESSAGE_LOGIN);
							loginWidget.hide();
							Dispatcher.forwardEvent(DGWATCHEvents.ZOOM_TO_CENTER);
                            Dispatcher.forwardEvent(DGWATCHEvents.LOGIN_SUCCESS, user);
						} else {
							loginWidget.setStatusLoginFinder(
									EnumLoginStatus.STATUS_NO_LOGIN,
									EnumLoginStatus.STATUS_MESSAGE_NOT_LOGIN);
						}
					}

					public void onFailure(Throwable caught) {
                        boolean handled = false;
                        if (caught instanceof ApplicationException) {
                            ApplicationException appException = (ApplicationException) caught;
                            if (appException.getDetailedMessage().contains("it.geosolutions.georepo.security.exception.AuthException")) {
                                // Authentication failed
                                handled = true;
                                loginWidget.resetPassword();
                                loginWidget.setStatusLoginFinder(
                                        EnumLoginStatus.STATUS_LOGIN_ERROR,
                                        EnumLoginStatus.STATUS_MESSAGE_NOT_LOGIN);
                            }
                        }

                        if (!handled) {
                            loginWidget.setStatusLoginFinder(
                                    EnumLoginStatus.STATUS_LOGIN_ERROR,
                                    EnumLoginStatus.STATUS_MESSAGE_LOGIN_ERROR);
                        }
					}
				});
	}

	private void onShowLoginWidget() {
		this.loginWidget.show();

	}

//	private void bindUser(User user) {
//		this.userManagementWidget.getUserInfo().unBindModel();
//		this.userManagementWidget.getUserInfo().bindModel(user);
//		this.userManagementWidget.enableButtons();
//
//		Dispatcher.forwardEvent(DGWATCHEvents.USER_SELECTED, user);
//
//		// ///////////////////////////
//		// Reset the grids contents
//		// ///////////////////////////
//
//		Dispatcher.forwardEvent(DGWATCHEvents.RESET_AOI_GRID);
//		Dispatcher.forwardEvent(DGWATCHEvents.RESET_RSS_GRID);
//		// Dispatcher.forwardEvent(DGWATCHEvents.ERASE_AOI_FEATURES);
//	}

}
