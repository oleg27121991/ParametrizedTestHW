package by.veremei.test;

import by.veremei.data.Language;
import by.veremei.pages.YouTubePage;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Configuration.baseUrl;

@DisplayName("Параметризованные wеб-тесты (не на поиск в яндексе или гугле)")
public class ParametrizedTest extends BaseTest {
    YouTubePage youTubePage = new YouTubePage();

    @BeforeEach
    void setUp() {
        Configuration.baseUrl = "https://www.youtube.com";
    }


    @ValueSource(strings = {
            "Любовь и голуби",
            "Вы кто такие",
            "Мама люба давай"
    })
    @DisplayName("При поисковом запросе ответ содержит элементы")
    @ParameterizedTest(name = "WEB: для поискового запроса {0} отдается не пустой список карточек")
    @Tag("WEB")
    void nonEmptyCardListAfterSearch(String queryLength) {
        youTubePage
                .openMainPage(baseUrl)
                .inputSearchValue(queryLength)

                .checkSearchResultIsNotEmpty();
    }

//    @CsvSource(value = {
//            "Любовь и голуби | Любовь и голуби (FullHD, комедия, реж. Владимир Меньшов, 1984 г.)",
//            "Дмитрий Тучс | Дмитрий Тучс — JUnit 5 Parallel test execution. Теория и практика",
//            "Мама люба давай | Serebro - Mama Luba (Official Video)"
//    }, delimiter = '|')
    @CsvFileSource(resources = "/test_data/cardShouldContainExpectedText.csv", delimiter = '|')
    @DisplayName("При поисковом запросе заголовок видео содержит текст")
    @ParameterizedTest(name = "WEB: для поискового запроса {0} в списке есть карточка с названием {1}")
    @Tag("WEB")
    void cardShouldContainExpectedText(String queryLength, String expectedName) {
        youTubePage
                .openMainPage(baseUrl)
                .inputSearchValue(queryLength)

                .checkQueryNameInCard(expectedName);
    }

    static Stream<Arguments> youTubeShouldDisplayCorrectBtn() {
        return Stream.of(
                Arguments.of(
                        Language.BY,
                        List.of("Галоўная старонка", "Shorts", "Падпіскі", "Вы", "Гісторыя", "Папулярныя", "Музыка", "Гульні", "Спорт", "Агляд каналаў", "YouTube Premium", "YouTube Music", "YouTube Kids", "Налады", "Гісторыя скаргаў", "Даведка", "Адправіць водгук")
                ),
                Arguments.of(
                        Language.MNG,
                        List.of("Нүүр", "Shorts", "Захиалгууд", "Та", "Түүх", "Тренд", "Хөгжим", "Тоглоом", "Спорт", "Суваг хайх", "YouTube Premium", "YouTube Music", "YouTube Kids", "Тохиргоо", "Тайлангийн түүх", "Тусламж", "Санал хүсэлт илгээх")
                ),
                Arguments.of(
                        Language.RU,
                        List.of("Главная", "Shorts", "Подписки", "Вы", "История", "В тренде", "Музыка", "Видеоигры", "Спорт", "Каталог каналов", "YouTube Premium", "YouTube Music", "YouTube Детям", "Настройки", "Жалобы", "Справка", "Отправить отзыв")
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Проверка локализации кнопок на главной странице")
    @Tag("WEB")
    void youTubeShouldDisplayCorrectBtn(Language language, List<String> expectedBtn) {
        youTubePage
                .openMainPage(baseUrl)
                .changeLangFromTopbarMenu(language.description)

                .checkButtonLocalization(expectedBtn);
    }

    @Disabled("Disabled until bug #777 has been fixed")
    @Test
    @DisplayName("Проверка отображения датапровайдерa @Disabled")
    void testWillBeSkipped() {
        youTubePage
                .openMainPage(baseUrl);

    }
}
