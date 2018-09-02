package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class ResourcesActivityInstTests extends ActivityInstrumentationTestCase2<ResourcesActivity> {

    public ResourcesActivityInstTests() {
        super(ResourcesActivity.class);
    }

    public void testActivityExists() {
        ResourcesActivity resourcesActivity = getActivity();
        assertNotNull(resourcesActivity);
    }

    public void testMainScrollViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        ScrollView scrollView = (ScrollView) resourcesActivity.findViewById(R.id.scrollMain);
        assertNotNull(scrollView);
    }

    public void testGlobeImageViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        ImageView imageView = (ImageView) resourcesActivity.findViewById(R.id.imageView);
        assertNotNull(imageView);
    }

    public void testExploreTitleTextViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        TextView textView = (TextView) resourcesActivity.findViewById(R.id.textView6);
        assertNotNull(textView);
    }

    public void testExploreDescriptionTextViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        TextView textView = (TextView) resourcesActivity.findViewById(R.id.textView9);
        assertNotNull(textView);
    }

    public void testKCLModulesImageViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        ImageView imageView = (ImageView) resourcesActivity.findViewById(R.id.imageView2);
        assertNotNull(imageView);
    }

    public void testKCLModulesTitleTextViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        TextView textView = (TextView) resourcesActivity.findViewById(R.id.textView7);
        assertNotNull(textView);
    }

    public void testKCLModulesDescriptionTextViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        TextView textView = (TextView) resourcesActivity.findViewById(R.id.textView8);
        assertNotNull(textView);
    }

    public void testKCLResourcesImageViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        ImageView imageView = (ImageView) resourcesActivity.findViewById(R.id.imageView3);
        assertNotNull(imageView);
    }

    public void testKCLResourcesTitleTextViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        TextView textView = (TextView) resourcesActivity.findViewById(R.id.textView10);
        assertNotNull(textView);
    }

    public void testKCLResourcesDescriptionTextViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        TextView textView = (TextView) resourcesActivity.findViewById(R.id.textView11);
        assertNotNull(textView);
    }

    public void testDuolingoImageViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        ImageView imageView = (ImageView) resourcesActivity.findViewById(R.id.imageView4);
        assertNotNull(imageView);
    }

    public void testDuolingoTitleTextViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        TextView textView = (TextView) resourcesActivity.findViewById(R.id.textView12);
        assertNotNull(textView);
    }

    public void testDuolingoDescriptionTextViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        TextView textView = (TextView) resourcesActivity.findViewById(R.id.textView13);
        assertNotNull(textView);
    }

    public void testBBCImageViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        ImageView imageView = (ImageView) resourcesActivity.findViewById(R.id.imageView5);
        assertNotNull(imageView);
    }

    public void testBBCTitleTextViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        TextView textView = (TextView) resourcesActivity.findViewById(R.id.textView14);
        assertNotNull(textView);
    }

    public void testBBCDescriptionTextViewExists() {
        ResourcesActivity resourcesActivity = getActivity();
        TextView textView = (TextView) resourcesActivity.findViewById(R.id.textView15);
        assertNotNull(textView);
    }
}
