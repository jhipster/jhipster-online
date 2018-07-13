import { browser, by, element } from 'protractor';
import { HomePage, NavBarPage } from '../page-objects/jhi-page-objects';

describe('home menu', () => {
    let homePage: HomePage;
    let navBarPage: NavBarPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage(true);
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
        homePage = new HomePage();
    });

    it('should redirect to home', () => {
        homePage.clickOnHome();

        const expect1 = /Welcome to JHipster Online/;
        element
            .all(by.css('h1'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });

    it('should redirect to configure', () => {
        homePage.clickOnConfig();

        const expect1 = /No GitHub or GitLab configuration available|GitHub configuration|Gitlab configuration/;
        element
            .all(by.css('h2'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });

    it('should redirect to create application', () => {
        homePage.clickOnCreateApp();

        const expect1 = /Application generation/;
        element
            .all(by.css('h2'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });

    it('should redirect to design entities', () => {
        homePage.clickOnDesignEntities();

        const expect1 = /Design Entities/;
        element
            .all(by.css('h2'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });

    it('should redirect to continuous integration', () => {
        homePage.clickOnCiCd();

        const expect1 = /Continuous Integration/;
        element
            .all(by.css('h2'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });
});
