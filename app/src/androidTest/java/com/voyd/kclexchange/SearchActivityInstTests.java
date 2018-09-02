package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.LinearLayout;

public class SearchActivityInstTests extends ActivityInstrumentationTestCase2<SearchActivity> {

    public SearchActivityInstTests() {
        super(SearchActivity.class);
    }

    public void testActivityExists() {
        SearchActivity searchActivity = getActivity();
        assertNotNull(searchActivity);
    }

    public void testSearchResultLinearLayoutExists() {
        SearchActivity searchActivity = getActivity();
        LinearLayout linearLayout = (LinearLayout) searchActivity.findViewById(R.id.linLayResultsHolder);
        assertNotNull(linearLayout);
    }
}
