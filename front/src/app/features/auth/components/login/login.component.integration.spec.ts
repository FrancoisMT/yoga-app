import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { expect } from '@jest/globals';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';


// Début de la suite de tests pour le composant LoginComponent
describe('LoginComponent', () => {
    let component: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;
    let authService: AuthService;
    let router: Router;
    let sessionService: SessionService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [LoginComponent],
            imports: [
                HttpClientTestingModule,
                MatIconModule,
                MatFormFieldModule,
                BrowserAnimationsModule,
                MatInputModule,
                MatCardModule,
                MatButtonModule,
                FormsModule,
                ReactiveFormsModule
            ],
            providers: [
                LoginComponent,
                FormBuilder,
                { provide: AuthService, useValue: { login: jest.fn() } },
                { provide: Router, useValue: { navigate: jest.fn() } },
                { provide: SessionService, useValue: { logIn: jest.fn(), $isLogged: jest.fn() } },
            ],
        });
        fixture = TestBed.createComponent(LoginComponent);
        component = TestBed.inject(LoginComponent);
        authService = TestBed.inject(AuthService);
        router = TestBed.inject(Router);
        sessionService = TestBed.inject(SessionService);
        fixture.detectChanges();

    });

    it('should call login method on submit on success', () => {
        const loginResponse = {} as any;
        const authSpy = jest.spyOn(authService, 'login').mockReturnValueOnce(of(loginResponse));
        const sessionSpy = jest.spyOn(sessionService, 'logIn');

        component.form.setValue({ email: 'test@example.com', password: 'password' });

        component.submit();

        expect(authSpy).toHaveBeenCalledWith({ email: 'test@example.com', password: 'password' });
        expect(sessionSpy).toHaveBeenCalledWith(loginResponse);
    });

    it('should set onError to true on login failure', () => {
        jest.spyOn(authService, 'login').mockReturnValueOnce(throwError(() => new Error('error')));
        component.form.setValue({ email: 'test@example.com', password: 'password' });

        component.submit();

        expect(component.onError).toBe(true);
    });

    describe('Form validation', () => {

        it('should require valid email', () => {

            component.form.setValue({
                "email": "invalidemail",
                "password": "testPassword"
            });

            expect(component.form.valid).toEqual(false);
        });

        it('should require valid password', () => {

            component.form.setValue({
                "email": "test@valid.fr",
                "password": ""
            });

            expect(component.form.valid).toEqual(false);
        });

        it('should have a valid form', () => {

            component.form.setValue({
                "email": "test@valid.fr",
                "password": "testPassword"
            });

            expect(component.form.valid).toEqual(true);
        });


    })

});
