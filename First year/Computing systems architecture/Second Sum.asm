bits 32
global suma

segment code use 32 public code
    suma:   
        mov eax,[esp+4]
        mov ebx,[esp+8]
        add eax,ebx
        ret