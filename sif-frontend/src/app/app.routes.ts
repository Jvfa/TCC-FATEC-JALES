import { Routes } from '@angular/router';

// Importe os componentes que você criou
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { PacienteList } from './pages/paciente-list/paciente-list';
import { PacienteDetail } from './pages/paciente-detail/paciente-detail';
import { PacienteForm } from './pages/paciente-form/paciente-form';
import { NotFound } from './shared/not-found/not-found';
import { ProcessoForm } from './pages/processo-form/processo-form';
import { MedicamentoList } from './pages/medicamento-list/medicamento-list';
import { MedicamentoForm } from './pages/medicamento-form/medicamento-form';
import { roleGuard } from './guards/role-guard';
import { ProcessoDetail } from './pages/processo-detail/processo-detail';

export const routes: Routes = [
    // Rotas de Autenticação e Dashboard
    { path: 'login', component: Login },
    { path: 'dashboard', component: Dashboard },

    // --- ROTAS DE PACIENTES (ORDEM CORRIGIDA) ---

    // Rota para a lista principal
    { path: 'pacientes', component: PacienteList },

    // Rota mais específica: 'novo' vem antes de ':id'
    { path: 'pacientes/novo', component: PacienteForm },

    // Rota mais longa e específica: vem antes da rota mais genérica de edição
    { path: 'pacientes/:pacienteId/processos/novo', component: ProcessoForm },

    // Rota mais longa: editar um paciente específico
    { path: 'pacientes/:id/editar', component: PacienteForm },

    // Rota mais genérica com parâmetro: por último no grupo de pacientes
    { path: 'pacientes/:id', component: PacienteDetail },

    { path: 'medicamentos', component: MedicamentoList },
    { path: 'medicamentos/novo', component: MedicamentoForm },
    { path: 'medicamentos/:id/editar', component: MedicamentoForm },

    {
        path: 'medicamentos',
        component: MedicamentoList,
        canActivate: [roleGuard], // Aplica o guarda
        data: { roles: ['ADMINISTRADOR', 'FARMACEUTICO'] } // Define os perfis permitidos
    },
    {
        path: 'medicamentos/novo',
        component: MedicamentoForm,
        canActivate: [roleGuard],
        data: { roles: ['ADMINISTRADOR', 'FARMACEUTICO'] }
    },
    {
        path: 'medicamentos/:id/editar',
        component: MedicamentoForm,
        canActivate: [roleGuard],
        data: { roles: ['ADMINISTRADOR', 'FARMACEUTICO'] }
    },

    { path: 'processos/:id', component: ProcessoDetail }, 


    // --- ROTAS PADRÃO E DE ERRO ---
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: '**', component: NotFound } // Rota "Coringa" sempre por último
];