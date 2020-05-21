package com.scruman.ui;

import com.scruman.AppConstants;
import com.scruman.app.HasLogger;
import com.scruman.backend.entity.Project;
import com.scruman.backend.entity.Sprint;
import com.scruman.backend.entity.User;
import com.scruman.backend.security.SecurityUtils;
import com.scruman.backend.service.ProjectService;
import com.scruman.backend.service.SprintService;
import com.scruman.backend.service.UserService;
import com.scruman.ui.components.FlexBoxLayout;
import com.scruman.ui.components.UserProjectsComboBox;
import com.scruman.ui.components.navigation.bar.AppBar;
import com.scruman.ui.components.navigation.drawer.NaviDrawer;
import com.scruman.ui.components.navigation.drawer.NaviItem;
import com.scruman.ui.components.navigation.drawer.NaviMenu;
import com.scruman.ui.util.UIUtils;
import com.scruman.ui.util.css.FlexDirection;
import com.scruman.ui.util.css.Overflow;
import com.scruman.ui.views.*;
import com.scruman.ui.views.backlog.ProductBacklog;
import com.scruman.ui.views.backlog.SprintBacklog;
import com.scruman.ui.views.settings.Settings;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.scruman.ui.util.UIUtils.IMG_PATH;

@CssImport(value = "./styles/components/charts.css", themeFor = "vaadin-chart", include = "vaadin-chart-default-theme")
@CssImport(value = "./styles/components/floating-action-button.css", themeFor = "vaadin-button")
@CssImport(value = "./styles/components/grid.css", themeFor = "vaadin-grid")
@CssImport("./styles/lumo/border-radius.css")
@CssImport("./styles/lumo/icon-size.css")
@CssImport("./styles/lumo/margin.css")
@CssImport("./styles/lumo/padding.css")
@CssImport("./styles/lumo/shadow.css")
@CssImport("./styles/lumo/spacing.css")
@CssImport("./styles/lumo/typography.css")
@CssImport("./styles/misc/box-shadow-borders.css")
@CssImport(value = "./styles/styles.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends FlexBoxLayout
		implements RouterLayout, PageConfigurator, AfterNavigationObserver, HasLogger {

	private static final Logger log = LoggerFactory.getLogger(MainLayout.class);
	private static final String CLASS_NAME = "root";

	private UserService userService;
	private SprintService sprintService;
	private ProjectService projectService;

	private FlexBoxLayout row;
	private NaviDrawer naviDrawer;
	private FlexBoxLayout column;

	private Div appHeaderInner;
	private FlexBoxLayout viewContainer;

	private AppBar appBar;

	@Autowired
	public MainLayout(UserService userService, SprintService sprintService, ProjectService projectService) {
		this.userService = userService;
		this.sprintService = sprintService;
		this.projectService = projectService;

		VaadinSession.getCurrent()
				.setErrorHandler((ErrorHandler) errorEvent -> {
					log.error("Uncaught UI exception",
							errorEvent.getThrowable());
					Notification.show(
							"We are sorry, but an internal error occurred");
				});

		addClassName(CLASS_NAME);
		setFlexDirection(FlexDirection.COLUMN);
		setSizeFull();

		appBar = new AppBar("");

		// Initialise the UI building blocks
		initStructure();

		// Populate the navigation drawer
		initNavItems();

		// Configure the headers and footers (optional)
		initHeadersAndFooters();
	}

	/**
	 * Initialise the required components and containers.
	 */
	private void initStructure() {
		naviDrawer = new NaviDrawer();

		viewContainer = new FlexBoxLayout();
		viewContainer.addClassName(CLASS_NAME + "__view-container");
		viewContainer.setOverflow(Overflow.HIDDEN);

		column = new FlexBoxLayout(viewContainer);
		column.addClassName(CLASS_NAME + "__column");
		column.setFlexDirection(FlexDirection.COLUMN);
		column.setFlexGrow(1, viewContainer);
		column.setOverflow(Overflow.HIDDEN);

		row = new FlexBoxLayout(naviDrawer, column);
		row.addClassName(CLASS_NAME + "__row");
		row.setFlexGrow(1, column);
		row.setOverflow(Overflow.HIDDEN);
		add(row);
		setFlexGrow(1, row);
	}

	/**
	 * Initialise the navigation items.
	 */
	private void initNavItems() {
		NaviMenu menu = naviDrawer.getMenu();
		menu.addNaviItem(VaadinIcon.HOME, "Home", Home.class);

		if (SecurityUtils.isUserLoggedIn()) {
			menu.addNaviItem(VaadinIcon.CLIPBOARD_TEXT, "Projects", Projects.class);

			UserProjectsComboBox userProjectsComboBox = appBar.getUserProjectsComboBox();
			if (userProjectsComboBox.isEmpty()) {
				User currentUser = userService.getCurrentUser();
				if (currentUser.getLastOpenedProject() != null) {
					userProjectsComboBox.setValue(currentUser.getLastOpenedProject());
				}
			}

			if (!userProjectsComboBox.isEmpty()) {
				Project currentProject = userProjectsComboBox.getValue();
				List<Sprint> projectSprints = sprintService.findAllByProjectId(currentProject.getId());

				NaviItem sprints = menu.addNaviItemWithoutLink(VaadinIcon.TASKS, "Sprints");
				for (Sprint s : projectSprints) {
					menu.addNaviItem(sprints, s.getTitle(),
							new RouterLink(null, SprintBacklog.class, s.getId().toString()),
							s.getId().toString());
				}

				menu.addNaviItem(VaadinIcon.CLIPBOARD_TEXT, "Product Backlog", ProductBacklog.class);
			}

			menu.addNaviItem(VaadinIcon.GROUP, "Members", Members.class);
			menu.addNaviItem(VaadinIcon.COG_O, "Settings", Settings.class);
			menu.addNaviItem(VaadinIcon.SIGN_OUT, "Logout", AppConstants.LOGOUT_URL);
		} else {
			menu.addNaviItem(VaadinIcon.SIGN_IN_ALT, "Register", Register.class);
			menu.addNaviItem(VaadinIcon.SIGN_IN, "Log in", Login.class);
		}
	}

	/**
	 * Configure the app's inner and outer headers and footers.
	 */
	private void initHeadersAndFooters() {
		// setAppHeaderOuter();
		// setAppFooterInner();
		// setAppFooterOuter();

		// Default inner header setup:
		// - When using tabbed navigation the view title, user avatar and main menu button will appear in the TabBar.
		// - When tabbed navigation is turned off they appear in the AppBar.

		UIUtils.setTheme(Lumo.DARK, appBar);
		setAppHeaderInner(appBar);

		UserProjectsComboBox userProjectsComboBox = appBar.getUserProjectsComboBox();
		if (SecurityUtils.isUserLoggedIn()) {
			userProjectsComboBox.setProjects(userService.getCurrentUserProjects());

			User currentUser = userService.getCurrentUser();
			if (currentUser.getLastOpenedProject() != null) {
				userProjectsComboBox.setValue(currentUser.getLastOpenedProject());
			}

			userProjectsComboBox.addValueChangeListener(e -> {
				userService.saveLastOpenedProject(currentUser, e.getValue());

				reloadMenu();
				UI.getCurrent().navigate(Home.class);
			});
			userProjectsComboBox.setVisible(true);

			Image avatar = appBar.getAvatar();
			avatar.setSrc(IMG_PATH + "avatar.png");

		} else {
			userProjectsComboBox.setVisible(false);
			appBar.getAvatar().setSrc(IMG_PATH + "avatar_anonymous.png");
		}
	}

	private void setAppHeaderInner(Component... components) {
		if (appHeaderInner == null) {
			appHeaderInner = new Div();
			appHeaderInner.addClassName("app-header-inner");
			column.getElement().insertChild(0, appHeaderInner.getElement());
		}
		appHeaderInner.removeAll();
		appHeaderInner.add(components);
	}

	@Override
	public void configurePage(InitialPageSettings settings) {
		settings.addMetaTag("apple-mobile-web-app-capable", "yes");
		settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");

		settings.addFavIcon("icon", "frontend/images/favicons/favicon.ico",
				"256x256");
	}

	@Override
	public void showRouterLayoutContent(HasElement content) {
		this.viewContainer.getElement().appendChild(content.getElement());
	}

	public NaviDrawer getNaviDrawer() {
		return naviDrawer;
	}

	public static MainLayout get() {
		return (MainLayout) UI.getCurrent().getChildren()
				.filter(component -> component.getClass() == MainLayout.class)
				.findFirst().get();
	}

	public AppBar getAppBar() {
		return appBar;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		NaviItem active = getActiveItem(event);
		if (active != null) {
			getAppBar().setTitle(active.getText());
		}
	}

	private NaviItem getActiveItem(AfterNavigationEvent e) {
		for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
			if (item.isHighlighted(e)) {
				return item;
			}
		}
		return null;
	}

	private void reloadMenu() {
		NaviMenu menu = naviDrawer.getMenu();
		menu.removeAll();

		initNavItems();
	}


}