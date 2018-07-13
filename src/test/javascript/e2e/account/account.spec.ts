import { browser, element, by } from 'protractor';
import { NavBarPage, SignInPage, PasswordPage, SettingsPage } from './../page-objects/jhi-page-objects';

describe('account', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let passwordPage: PasswordPage;
    let settingsPage: SettingsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage(true);
        browser.waitForAngular();
    });

    it('should fail to login with bad password', () => {
        const expect1 = /Welcome to JHipster Online/;
        element
            .all(by.css('h1'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
        signInPage = navBarPage.getSignInPage();
        signInPage.autoSignInUsing('admin', 'foo');

        const expect2 = /Failed to sign in!/;
        element
            .all(by.css('.alert-danger'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect2);
            });
    });

    it('should login successfully with admin account', () => {
        const expect1 = /Login/;
        element
            .all(by.css('.modal-content label'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect1);
            });
        signInPage.clearUserName();
        signInPage.setUserName('admin');
        signInPage.clearPassword();
        signInPage.setPassword('admin');
        signInPage.login();

        browser.waitForAngular();

        const expect2 = /You are logged in as user "admin"/;
        element
            .all(by.css('.alert-success span'))
            .getText()
            .then(value => {
                expect(value).toMatch(expect2);
            });
    });
    it('should be able to update settings', () => {
        settingsPage = navBarPage.getSettingsPage();

        const expect1 = /User settings for \[admin\]/;
        settingsPage.getTitle().then(value => {
            expect(value).toMatch(expect1);
        });
        settingsPage.save();

        const expect2 = /Settings saved!/;
        element
            .all(by.css('.alert-success'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect2);
            });
    });

    it('should fail to update password when using incorrect current password', () => {
        passwordPage = navBarPage.getPasswordPage();

        expect(passwordPage.getTitle()).toMatch(/Password for \[admin\]/);

        passwordPage.setCurrentPassword('wrong_current_password');
        passwordPage.setPassword('new_password');
        passwordPage.setConfirmPassword('new_password');
        passwordPage.save();

        const expect2 = /An error has occurred! The password could not be changed./;
        element
            .all(by.css('.alert-danger'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect2);
            });
        settingsPage = navBarPage.getSettingsPage();
    });

    it('should be able to update password', () => {
        passwordPage = navBarPage.getPasswordPage();

        expect(passwordPage.getTitle()).toMatch(/Password for \[admin\]/);

        passwordPage.setCurrentPassword('admin');
        passwordPage.setPassword('newpassword');
        passwordPage.setConfirmPassword('newpassword');
        passwordPage.save();

        const expect2 = /Password changed!/;
        element
            .all(by.css('.alert-success'))
            .first()
            .getText()
            .then(value => {
                expect(value).toMatch(expect2);
            });
        navBarPage.autoSignOut();
        navBarPage.goToSignInPage();
        signInPage.autoSignInUsing('admin', 'newpassword');

        // change back to default
        navBarPage.goToPasswordMenu();
        passwordPage.setCurrentPassword('newpassword');
        passwordPage.setPassword('admin');
        passwordPage.setConfirmPassword('admin');
        passwordPage.save();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
