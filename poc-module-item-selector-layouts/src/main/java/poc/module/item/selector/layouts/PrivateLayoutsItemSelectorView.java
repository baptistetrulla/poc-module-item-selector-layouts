package poc.module.item.selector.layouts;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	    property = {"item.selector.view.order:Integer=1"},
	    service = {ItemSelectorView.class}
	)
public class PrivateLayoutsItemSelectorView implements ItemSelectorView<LayoutItemSelectorCriterion>{

	@Override
	public Class<LayoutItemSelectorCriterion> getItemSelectorCriterionClass() 
	{
	    return LayoutItemSelectorCriterion.class;
	}

	public ServletContext getServletContext() {
		System.out.println("PrivateLayoutsItemSelectorView getServletContext");

	    return _servletContext;
	}

	@Override            
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		System.out.println("PrivateLayoutsItemSelectorView getSupportedItemSelectorReturnTypes");

	    return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		System.out.println("PrivateLayoutsItemSelectorView getTitle");

		return "Layout custom selector";
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		System.out.println("PrivateLayoutsItemSelectorView isVisible");
		return false;
	}

	@Override
	public void renderHTML(ServletRequest servletRequest, ServletResponse servletResponse,
			LayoutItemSelectorCriterion itemSelectorCriterion, PortletURL portletURL, String itemSelectedEventName,
			boolean search) throws IOException, ServletException {
		System.out.println("PrivateLayoutsItemSelectorView renderHTML");
		servletRequest.setAttribute("LAYOUT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT", itemSelectedEventName);
	        ServletContext servletContext = getServletContext();

	        RequestDispatcher requestDispatcher =
	            servletContext.getRequestDispatcher("/view.jsp");

	        requestDispatcher.include(servletRequest, servletResponse);		
	}
	
	@Override
	public boolean isShowSearch() {
		System.out.println("PrivateLayoutsItemSelectorView isShowSearch");

	    return false;
	}

	@Reference(
		    target =
		    "(osgi.web.symbolicname=com.liferay.layout.item.selector.web)",
		    unbind = "-"
		)
		public void setServletContext(ServletContext servletContext) {
		    _servletContext = servletContext;
		}
	
	private static final List<ItemSelectorReturnType>
    _supportedItemSelectorReturnTypes =
    Collections.unmodifiableList(
        ListUtil.fromArray(
            new ItemSelectorReturnType[] {
					new URLItemSelectorReturnType(),
					new UUIDItemSelectorReturnType()
            }));

	private ServletContext _servletContext;
	
}
