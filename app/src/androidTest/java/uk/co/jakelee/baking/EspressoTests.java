package uk.co.jakelee.baking;

import android.content.Context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import uk.co.jakelee.baking.list.RecipeListActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTests {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("uk.co.jakelee.baking", appContext.getPackageName());
    }

    @Test
    public void checkFullFlow() {
        // Check recipe is visible
        onView(withId(R.id.recipe_list)).check(matches(isDisplayed()));
        RecyclerView recipeList = mActivityRule.getActivity().findViewById(R.id.recipe_list);
        assertNotEquals(recipeList.getAdapter().getItemCount(), 0);

        // Check tapping it opens the recipe page
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check ingredients + steps are visible
        onView(withId(R.id.recipe_ingredients)).check(matches(isDisplayed()));
        onView(withId(R.id.step_list)).check(matches(isDisplayed()));

        // Check tapping step opens the step page
        onView(withId(R.id.step_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check all elements are visible
        onView(withId(R.id.item_detail)).check(matches(not(withText(""))));
    }
}
