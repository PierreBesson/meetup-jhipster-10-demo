import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('TravelReservation e2e test', () => {

    let navBarPage: NavBarPage;
    let travelReservationDialogPage: TravelReservationDialogPage;
    let travelReservationComponentsPage: TravelReservationComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load TravelReservations', () => {
        navBarPage.goToEntity('travel-reservation');
        travelReservationComponentsPage = new TravelReservationComponentsPage();
        expect(travelReservationComponentsPage.getTitle()).toMatch(/gatewayApp.travelReservation.home.title/);

    });

    it('should load create TravelReservation dialog', () => {
        travelReservationComponentsPage.clickOnCreateButton();
        travelReservationDialogPage = new TravelReservationDialogPage();
        expect(travelReservationDialogPage.getModalTitle()).toMatch(/gatewayApp.travelReservation.home.createOrEditLabel/);
        travelReservationDialogPage.close();
    });

    it('should create and save TravelReservations', () => {
        travelReservationComponentsPage.clickOnCreateButton();
        travelReservationDialogPage.setTourCodeInput('tourCode');
        expect(travelReservationDialogPage.getTourCodeInput()).toMatch('tourCode');
        travelReservationDialogPage.setNumberOfPersonsInput('5');
        expect(travelReservationDialogPage.getNumberOfPersonsInput()).toMatch('5');
        travelReservationDialogPage.getNeedVisaInput().isSelected().then(function (selected) {
            if (selected) {
                travelReservationDialogPage.getNeedVisaInput().click();
                expect(travelReservationDialogPage.getNeedVisaInput().isSelected()).toBeFalsy();
            } else {
                travelReservationDialogPage.getNeedVisaInput().click();
                expect(travelReservationDialogPage.getNeedVisaInput().isSelected()).toBeTruthy();
            }
        });
        travelReservationDialogPage.getClientConfirmedInput().isSelected().then(function (selected) {
            if (selected) {
                travelReservationDialogPage.getClientConfirmedInput().click();
                expect(travelReservationDialogPage.getClientConfirmedInput().isSelected()).toBeFalsy();
            } else {
                travelReservationDialogPage.getClientConfirmedInput().click();
                expect(travelReservationDialogPage.getClientConfirmedInput().isSelected()).toBeTruthy();
            }
        });
        travelReservationDialogPage.getProviderConfirmedInput().isSelected().then(function (selected) {
            if (selected) {
                travelReservationDialogPage.getProviderConfirmedInput().click();
                expect(travelReservationDialogPage.getProviderConfirmedInput().isSelected()).toBeFalsy();
            } else {
                travelReservationDialogPage.getProviderConfirmedInput().click();
                expect(travelReservationDialogPage.getProviderConfirmedInput().isSelected()).toBeTruthy();
            }
        });
        travelReservationDialogPage.getAgencyConfirmedInput().isSelected().then(function (selected) {
            if (selected) {
                travelReservationDialogPage.getAgencyConfirmedInput().click();
                expect(travelReservationDialogPage.getAgencyConfirmedInput().isSelected()).toBeFalsy();
            } else {
                travelReservationDialogPage.getAgencyConfirmedInput().click();
                expect(travelReservationDialogPage.getAgencyConfirmedInput().isSelected()).toBeTruthy();
            }
        });
        travelReservationDialogPage.setNotesInput('notes');
        expect(travelReservationDialogPage.getNotesInput()).toMatch('notes');
        travelReservationDialogPage.save();
        expect(travelReservationDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TravelReservationComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-travel-reservation div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TravelReservationDialogPage {
    modalTitle = element(by.css('h4#myTravelReservationLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    tourCodeInput = element(by.css('input#field_tourCode'));
    numberOfPersonsInput = element(by.css('input#field_numberOfPersons'));
    needVisaInput = element(by.css('input#field_needVisa'));
    clientConfirmedInput = element(by.css('input#field_clientConfirmed'));
    providerConfirmedInput = element(by.css('input#field_providerConfirmed'));
    agencyConfirmedInput = element(by.css('input#field_agencyConfirmed'));
    notesInput = element(by.css('input#field_notes'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTourCodeInput = function (tourCode) {
        this.tourCodeInput.sendKeys(tourCode);
    }

    getTourCodeInput = function () {
        return this.tourCodeInput.getAttribute('value');
    }

    setNumberOfPersonsInput = function (numberOfPersons) {
        this.numberOfPersonsInput.sendKeys(numberOfPersons);
    }

    getNumberOfPersonsInput = function () {
        return this.numberOfPersonsInput.getAttribute('value');
    }

    getNeedVisaInput = function () {
        return this.needVisaInput;
    }
    getClientConfirmedInput = function () {
        return this.clientConfirmedInput;
    }
    getProviderConfirmedInput = function () {
        return this.providerConfirmedInput;
    }
    getAgencyConfirmedInput = function () {
        return this.agencyConfirmedInput;
    }
    setNotesInput = function (notes) {
        this.notesInput.sendKeys(notes);
    }

    getNotesInput = function () {
        return this.notesInput.getAttribute('value');
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
