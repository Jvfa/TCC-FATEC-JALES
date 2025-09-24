// DTO para a requisição de login
export interface LoginRequestDTO {
    login: string;
    senha: string;
}

// DTO para a resposta de login
export interface LoginResponseDTO {
    token: string;
}