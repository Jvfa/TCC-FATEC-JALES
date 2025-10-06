import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OcrService {
  private apiUrl = 'http://localhost:8090/api/ocr/upload';

  constructor(private http: HttpClient) { }

  uploadImagem(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('arquivo', file, file.name);

    return this.http.post(this.apiUrl, formData);
  }
}