import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common'; // <-- MUDANÇA 1: Importe o CommonModule aqui
import { AuthService } from '../../services/auth'; // Importe o seu AuthService

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule // <-- MUDANÇA 2: Adicione o CommonModule aos imports
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {

  // MUDANÇA 3: Injete o AuthService no construtor para que possamos usá-lo na classe e no HTML
  constructor(public authService: AuthService) { }

  /**
   * Este método delega a ação de logout para o serviço de autenticação.
   */
  logout(): void {
    this.authService.logout();
  }
}