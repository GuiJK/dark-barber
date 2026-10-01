import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { NgxMaskDirective } from 'ngx-mask';
import { ClientModelForm } from '../../client.models';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-client-form',
  imports: [
    FormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    NgxMaskDirective
  ],
  templateUrl: './client-form.component.html',
  styleUrl: './client-form.component.scss'
})
export class ClientFormComponent {

  @Input() client: ClientModelForm = { id: 0, name: '', email: '', phone: '' };

  @Output() clientSubmited = new EventEmitter<ClientModelForm>();

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Formulário válido, disparando evento:', this.client);
      this.clientSubmited.emit(this.client);
    } else {
      console.warn('Tentativa de envio com formulário inválido');
    }
  }

}
