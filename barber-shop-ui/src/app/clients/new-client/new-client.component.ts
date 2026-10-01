import { Component, Inject, OnDestroy } from '@angular/core';
import { SERVICES_TOKEN } from '../../services/service.token';
import { ICLientService } from '../../services/api-client/clients/iclients.service';
import { ClientsService } from '../../services/api-client/clients/clients.service';
import { ClientModelForm } from '../client.models';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { SnackbarManagerService } from '../../services/snackbar-manager.service';
import { ISnackbarManagerService } from '../../services/isnackbar-manager.service';
import { ClientFormComponent } from '../components/client-form/client-form.component';

@Component({
  selector: 'app-new-client',
  standalone: true,
  imports: [ClientFormComponent],
  templateUrl: './new-client.component.html',
  styleUrl: './new-client.component.scss',
  providers: [
    { provide: SERVICES_TOKEN.HTTP.CLIENT, useClass: ClientsService },
    { provide: SERVICES_TOKEN.SNACKBAR, useClass: SnackbarManagerService }
  ]
})
export class NewClientComponent implements OnDestroy {

  private httpSubscription?: Subscription;

  constructor(
    @Inject(SERVICES_TOKEN.HTTP.CLIENT) private readonly httpService: ICLientService,
    @Inject(SERVICES_TOKEN.SNACKBAR) private readonly snackBarManager: ISnackbarManagerService,
    private readonly router: Router
  ) { }

  ngOnDestroy(): void {
    if (this.httpSubscription) {
      this.httpSubscription.unsubscribe();
    }
  }

  onSubmitClient(value: ClientModelForm) {
    console.log('Componente Pai (NewClient) recebeu o evento com os dados:', value);

    const { id, ...request } = value;

    this.httpSubscription = this.httpService.save(request).subscribe({
      next: (response) => {
        console.log('Cliente salvo com sucesso no backend:', response);
        this.snackBarManager.show('Usuário cadastrado com sucesso');
        this.router.navigate(['clients/list']);
      },
      error: (err) => {
        console.error('Erro ao chamar a API de salvamento:', err);
        this.snackBarManager.show('Erro ao cadastrar cliente. Verifique os dados.');
      }
    });
  }
}
