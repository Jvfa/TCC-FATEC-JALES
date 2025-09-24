import { Routes } from '@angular/router';

import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { PacienteList } from './pages/paciente-list/paciente-list';
import { PacienteDetail } from './pages/paciente-detail/paciente-detail';
import { PacienteForm } from './pages/paciente-form/paciente-form';
import { NotFound} from './shared/not-found/not-found';

export const routes: Routes = [
    // Rotas de Autenticação e Dashboard
    { path: 'login', component: Login },
    { path: 'dashboard', component: Dashboard },

    // Rotas de Pacientes (CRUD)
    { path: 'pacientes', component: PacienteList},
    { path: 'pacientes/novo', component: PacienteForm },
    { path: 'pacientes/:id', component: PacienteDetail },
    { path: 'pacientes/:id/editar', component: PacienteForm },

    // Rotas padrão e de erro
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: '**', component: NotFound }
];
