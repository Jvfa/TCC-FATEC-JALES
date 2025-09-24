import { Component } from '@angular/core';
import { RouterLink } from '@angular/router'; // Import necessário para o routerLink funcionar no HTML
import { AuthService } from '../../services/auth'; // 1. Importe o seu AuthService

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink // Adicione o RouterLink aos imports
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {

  // 2. Injete o AuthService no construtor para que possamos usá-lo na classe
  constructor(private authService: AuthService) { }

  /**
   * 3. Crie o método logout() que será chamado pelo link no HTML.
   * Este método simplesmente delega a ação para o método logout() do serviço.
   */
  logout(): void {
    console.log('Chamando o método de logout...');
    this.authService.logout();
  }
}