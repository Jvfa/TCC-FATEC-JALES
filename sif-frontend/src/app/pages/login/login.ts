import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router'; // Para navegar para outra página
import { AuthService } from '../../services/auth'; // Nosso serviço de autenticação
import { LoginRequestDTO } from '../../models/auth.model'; // Nosso modelo de dados

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  // Objeto para armazenar os dados do formulário, vinculado com [(ngModel)]
  credentials: LoginRequestDTO = {
    login: '',
    senha: ''
  };

  errorMessage: string | null = null;

  // Injetamos o AuthService e o Router para podermos usá-los
  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  // Método chamado quando o formulário é enviado
  onSubmit(): void {
    this.errorMessage = null; // Limpa mensagens de erro antigas

    this.authService.login(this.credentials).subscribe({
      // Callback para o caso de SUCESSO na requisição
      next: (response) => {
        console.log('Login bem-sucedido!', response.token);
        // Navega para o dashboard após o login
        this.router.navigate(['/dashboard']);
      },
      // Callback para o caso de ERRO na requisição
      error: (err) => {
        console.error('Falha no login', err);
        this.errorMessage = 'Usuário ou senha inválidos. Tente novamente.';
      }
    });
  }
}