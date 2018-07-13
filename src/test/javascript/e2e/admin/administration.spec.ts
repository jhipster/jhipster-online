import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('administration', () => {
    let navBarPage: NavBarPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage(true);
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    beforeEach(() => {
        navBarPage.clickOnAdminMenu();
    });
    it('should load user management', () => {
        navBarPage.clickOnAdmin('user-management');
        const expect1 = /Users/;
        element
            .all(by.css('h2 span'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });

    it('should load metrics', () => {
        navBarPage.clickOnAdmin('jhi-metrics');
        const expect1 = /Application Metrics/;
        element
            .all(by.css('h2 span'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });

    it('should load health', () => {
        navBarPage.clickOnAdmin('jhi-health');
        const expect1 = /Health Checks/;
        element
            .all(by.css('h2 span'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });

    it('should load configuration', () => {
        navBarPage.clickOnAdmin('jhi-configuration');
        const expect1 = /Configuration/;
        element
            .all(by.css('h2'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });

    it('should load audits', () => {
        navBarPage.clickOnAdmin('audits');
        const expect1 = /Audits/;
        element
            .all(by.css('h2'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });

    it('should load logs', () => {
        navBarPage.clickOnAdmin('logs');
        const expect1 = /Logs/;
        element
            .all(by.css('h2'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
