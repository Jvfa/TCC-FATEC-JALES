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
import { authGuard } from './guards/auth.guard';
import { OcrUpload } from './pages/ocr-upload/ocr-upload';

export const routes: Routes = [
  // A rota de login não é protegida
  { path: 'login', component: Login },

  // --- ROTAS PROTEGIDAS PELO LOGIN ---
  { path: 'dashboard', component: Dashboard, canActivate: [authGuard] },
  { path: 'pacientes', component: PacienteList, canActivate: [authGuard] },
  { path: 'pacientes/novo', component: PacienteForm, canActivate: [authGuard] },
  { path: 'pacientes/:id', component: PacienteDetail, canActivate: [authGuard] },
  { path: 'pacientes/:id/editar', component: PacienteForm, canActivate: [authGuard] },
  { path: 'processos/:id', component: ProcessoDetail, canActivate: [authGuard] },
  { path: 'pacientes/:pacienteId/processos/novo', component: ProcessoForm, canActivate: [authGuard] },
  { path: 'ocr-upload', component: OcrUpload, canActivate: [authGuard] },
  
  // --- ROTAS PROTEGIDAS PELO LOGIN E PELO PERFIL ---
  {
    path: 'medicamentos',
    component: MedicamentoList,
    canActivate: [authGuard, roleGuard], // Adicionamos os dois guardas
    data: { roles: ['ADMINISTRADOR', 'FARMACEUTICO'] }
  },
  {
    path: 'medicamentos/novo',
    component: MedicamentoForm,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ADMINISTRADOR', 'FARMACEUTICO'] }
  },
  {
    path: 'medicamentos/:id/editar',
    component: MedicamentoForm,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ADMINISTRADOR', 'FARMACEUTICO'] }
  },

  // --- ROTAS PADRÃO E DE ERRO ---
  // A rota vazia agora redireciona para o dashboard (o guarda cuidará do resto)
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  // A rota coringa sempre por último
  { path: '**', component: NotFound }
];