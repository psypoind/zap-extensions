/*
 * Zed Attack Proxy (ZAP) and its related class files.
 * 
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 * 
 * Copyright 2017 The ZAP Development Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zaproxy.zap.extension.selenium;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import org.apache.commons.lang.Validate;

/**
 * A {@code ComboBoxModel} of {@link ProvidedBrowserUI}s.
 *
 * @since 1.1.0
 */
public class ProvidedBrowsersComboBoxModel extends AbstractListModel<ProvidedBrowserUI>
        implements ComboBoxModel<ProvidedBrowserUI> {

    private static final long serialVersionUID = -2353742370704132293L;

    /**
     * The {@code ProvidedBrowserUI}s contained in this model.
     */
    private final List<ProvidedBrowserUI> browsers;

    /**
     * The currently selected browser, {@code null} if no browser is selected.
     */
    private ProvidedBrowserUI selectedBrowser;

    /**
     * Constructs a {@code BrowsersComboBoxModel} with the given {@code browsers}.
     * <p>
     * The selected item will be set to the first browser of the given list of {@code browsers}.
     * 
     * @param browsers the browsers that will have this combo box model
     * @throws IllegalArgumentException if the given {@code List} of {@code browsers} is {@code null} or empty.
     */
    public ProvidedBrowsersComboBoxModel(List<ProvidedBrowserUI> browsers) {
        Validate.notEmpty(browsers);

        this.browsers = browsers;
        this.selectedBrowser = this.browsers.get(0);
    }

    @Override
    public ProvidedBrowserUI getSelectedItem() {
        return selectedBrowser;
    }

    /**
     * Convenience method that sets the selected browser using the browser ID.
     * <p>
     * No changes are done to the selected item if the browser with the given ID is not contained in this model.
     * 
     * @param providerBrowserId the id of the browser, {@code null} to clear the selection
     * @see #setSelectedBrowser(ProvidedBrowser)
     */
    public void setSelectedBrowser(String providerBrowserId) {
        if (providerBrowserId == null) {
            setSelectedBrowserImpl(null);
            return;
        }

        for (ProvidedBrowserUI browserUI : browsers) {
            if (providerBrowserId.equals(browserUI.getBrowser().getId())) {
                setSelectedBrowserImpl(browserUI);
                break;
            }
        }
    }

    /**
     * Convenience method that sets the selected item using the given {@code browser}.
     * <p>
     * No changes are done to the selected item if the given browser is not contained in this model.
     * 
     * @param browser the browser, {@code null} to clear the selection
     * @see #setSelectedItem(Object)
     */
    public void setSelectedBrowser(ProvidedBrowser browser) {
        if (browser == null) {
            setSelectedBrowserImpl(null);
            return;
        }

        for (ProvidedBrowserUI browserUI : browsers) {
            if (browser.equals(browserUI.getBrowser())) {
                setSelectedBrowserImpl(browserUI);
                break;
            }
        }
    }

    /**
     * Sets the given {@code item} as the selected item. Might be {@code null} to clear the selection.
     * <p>
     * No changes are done to the selected item if the given {@code item} is not contained in this model.
     * 
     * @throws IllegalArgumentException if {@code item} is not a {@code ProvidedBrowserUI} when non-{@code null}.
     */
    @Override
    public void setSelectedItem(Object item) {
        if (item != null && !(item instanceof ProvidedBrowserUI)) {
            throw new IllegalArgumentException("Parameter item must be of type ProvidedBrowserUI.");
        }

        ProvidedBrowserUI browser = (ProvidedBrowserUI) item;
        if (browser != null && !browsers.contains(browser)) {
            return;
        }

        setSelectedBrowserImpl(browser);
    }

    private void setSelectedBrowserImpl(ProvidedBrowserUI browser) {
        if ((selectedBrowser != null && !selectedBrowser.equals(browser)) || selectedBrowser == null && browser != null) {
            selectedBrowser = browser;
            fireContentsChanged(this, -1, -1);
        }
    }

    @Override
    public int getSize() {
        return browsers.size();
    }

    @Override
    public ProvidedBrowserUI getElementAt(int index) {
        return browsers.get(index);
    }

}