import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Observable, of, throwError } from 'rxjs';
import { expect } from '@jest/globals';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';


// Début de la suite de tests
describe('RegisterComponent', () => {
    let component: RegisterComponent;
    let fixture: ComponentFixture<RegisterComponent>;
    let authService: AuthService;
    let router: Router;

    beforeEach(() => {

        TestBed.configureTestingModule({
            declarations: [RegisterComponent],
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
                RegisterComponent,
                FormBuilder,
                { provide: AuthService, useValue: { register: jest.fn() } },
                { provide: Router, useValue: { navigate: jest.fn() } },
            ],
        });
        fixture = TestBed.createComponent(RegisterComponent);
        component = TestBed.inject(RegisterComponent);
        authService = TestBed.inject(AuthService);
        router = TestBed.inject(Router);
        fixture.detectChanges();

    });

    describe('Submit', () => {

        it('should call register method on successful registration', () => {
            // Given
            const formValues = {
                email: 'test@example.com',
                firstName: 'John',
                lastName: 'Doe',
                password: 'password123',
              };
              component.form.setValue(formValues); 
              jest.spyOn(authService, 'register').mockReturnValue(of(undefined));
        
              // When : Appel de la méthode à tester
              component.submit();
        
              // Then : Vérification que la méthode register du service AuthService a été appelée avec les bonnes valeurs
              expect(authService.register).toHaveBeenCalledWith(formValues);
            
        });

        it('should set onError to true when AuthService.register returns an error', () => {
            // Given 
            const formValues = {
              email: 'test@example.com',
              firstName: 'John',
              lastName: 'Doe',
              password: 'password123',
            };
            component.form.setValue(formValues); // Définition des valeurs du formulaire
            jest.spyOn(authService, 'register').mockImplementation(() => {
              return new Observable<void>((subscriber) => {
                subscriber.error(new Error('error')); 
              });
            });
      
            // When : Appel de la méthode à tester
            component.submit();
      
            // Then : Vérification que onError est défini sur true
            expect(component.onError).toBe(true);
          });
    })

});
