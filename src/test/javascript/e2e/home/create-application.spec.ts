import { GeneratorPage, HomePage, NavBarPage } from '../page-objects/jhi-page-objects';
import { browser, by, element } from 'protractor';

describe('generator', () => {
    let homePage: HomePage;
    let navBarPage: NavBarPage;
    let generatorPage: GeneratorPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage(true);
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
        homePage = new HomePage();
        homePage.clickOnCreateApp();
        browser.waitForAngular();
        generatorPage = new GeneratorPage();
    });

    it('should reset values', () => {
        const appNameDefaultValue = generatorPage.getAppName();

        generatorPage.setAppName('JHipsterOnlineFTW');
        generatorPage.clickOnReset();

        expect(generatorPage.getAppName()).toBe(appNameDefaultValue);
    });
});
