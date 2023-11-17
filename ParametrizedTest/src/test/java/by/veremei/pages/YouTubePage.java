package by.veremei.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class YouTubePage {
    private final SelenideElement inputSearch = $("#search-form #search"),
                divWithSearchResult = $("#container #primary"),
                btnTopbarMenu = $("#end #button"),
                linkOpenLanguageList = $("#container [role='link']", 1);
    private final ElementsCollection cardsWithVideos = $$("ytd-video-renderer .ytd-video-renderer"),
                listWithLanguage = $$("[role='link'] #label"),
                sideMenuCategoryNavigation = $$("#guide-inner-content #endpoint");
    public YouTubePage openMainPage (String url) {
        Selenide.open(url);

        return this;
    }

    public YouTubePage inputSearchValue(String text) {
        inputSearch.setValue(text).pressEnter();

        return this;
    }

    public void checkSearchResultIsNotEmpty() {
        divWithSearchResult.shouldNotBe(empty);
    }

    public void checkQueryNameInCard(String name) {
        cardsWithVideos.shouldHave(itemWithText(name));
    }

    public YouTubePage changeLangFromTopbarMenu(String name) {
        btnTopbarMenu.click();
        linkOpenLanguageList.click();
        listWithLanguage.findBy(text(name)).click();

        return this;
    }

    public void checkButtonLocalization(List<String> expectedBtn) {
        sideMenuCategoryNavigation.shouldHave(texts(expectedBtn));
    }
}
