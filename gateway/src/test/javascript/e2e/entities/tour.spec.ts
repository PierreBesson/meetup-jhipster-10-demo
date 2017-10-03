import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Tour e2e test', () => {

    let navBarPage: NavBarPage;
    let tourDialogPage: TourDialogPage;
    let tourComponentsPage: TourComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Tours', () => {
        navBarPage.goToEntity('tour');
        tourComponentsPage = new TourComponentsPage();
        expect(tourComponentsPage.getTitle()).toMatch(/gatewayApp.tour.home.title/);

    });

    it('should load create Tour dialog', () => {
        tourComponentsPage.clickOnCreateButton();
        tourDialogPage = new TourDialogPage();
        expect(tourDialogPage.getModalTitle()).toMatch(/gatewayApp.tour.home.createOrEditLabel/);
        tourDialogPage.close();
    });

    it('should create and save Tours', () => {
        tourComponentsPage.clickOnCreateButton();
        tourDialogPage.setCodeInput('code');
        expect(tourDialogPage.getCodeInput()).toMatch('code');
        tourDialogPage.setNameInput('name');
        expect(tourDialogPage.getNameInput()).toMatch('name');
        tourDialogPage.setNumberOfDaysInput('5');
        expect(tourDialogPage.getNumberOfDaysInput()).toMatch('5');
        tourDialogPage.setNumberOfNightsInput('5');
        expect(tourDialogPage.getNumberOfNightsInput()).toMatch('5');
        tourDialogPage.setDescriptionInput('description');
        expect(tourDialogPage.getDescriptionInput()).toMatch('description');
        tourDialogPage.setTimeOfDepartureInput(12310020012301);
        expect(tourDialogPage.getTimeOfDepartureInput()).toMatch('2001-12-31T02:30');
        tourDialogPage.setTimeOfReturnInput(12310020012301);
        expect(tourDialogPage.getTimeOfReturnInput()).toMatch('2001-12-31T02:30');
        tourDialogPage.setPriceInput('5');
        expect(tourDialogPage.getPriceInput()).toMatch('5');
        tourDialogPage.setCapacityInput('5');
        expect(tourDialogPage.getCapacityInput()).toMatch('5');
        tourDialogPage.save();
        expect(tourDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TourComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-tour div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TourDialogPage {
    modalTitle = element(by.css('h4#myTourLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    codeInput = element(by.css('input#field_code'));
    nameInput = element(by.css('input#field_name'));
    numberOfDaysInput = element(by.css('input#field_numberOfDays'));
    numberOfNightsInput = element(by.css('input#field_numberOfNights'));
    descriptionInput = element(by.css('input#field_description'));
    timeOfDepartureInput = element(by.css('input#field_timeOfDeparture'));
    timeOfReturnInput = element(by.css('input#field_timeOfReturn'));
    priceInput = element(by.css('input#field_price'));
    capacityInput = element(by.css('input#field_capacity'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCodeInput = function (code) {
        this.codeInput.sendKeys(code);
    }

    getCodeInput = function () {
        return this.codeInput.getAttribute('value');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    setNumberOfDaysInput = function (numberOfDays) {
        this.numberOfDaysInput.sendKeys(numberOfDays);
    }

    getNumberOfDaysInput = function () {
        return this.numberOfDaysInput.getAttribute('value');
    }

    setNumberOfNightsInput = function (numberOfNights) {
        this.numberOfNightsInput.sendKeys(numberOfNights);
    }

    getNumberOfNightsInput = function () {
        return this.numberOfNightsInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    setTimeOfDepartureInput = function (timeOfDeparture) {
        this.timeOfDepartureInput.sendKeys(timeOfDeparture);
    }

    getTimeOfDepartureInput = function () {
        return this.timeOfDepartureInput.getAttribute('value');
    }

    setTimeOfReturnInput = function (timeOfReturn) {
        this.timeOfReturnInput.sendKeys(timeOfReturn);
    }

    getTimeOfReturnInput = function () {
        return this.timeOfReturnInput.getAttribute('value');
    }

    setPriceInput = function (price) {
        this.priceInput.sendKeys(price);
    }

    getPriceInput = function () {
        return this.priceInput.getAttribute('value');
    }

    setCapacityInput = function (capacity) {
        this.capacityInput.sendKeys(capacity);
    }

    getCapacityInput = function () {
        return this.capacityInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
