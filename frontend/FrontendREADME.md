# Frontend - Visitor Access Control

Frontend de la aplicación de control de ingresos desarrollada para el challenge técnico de Geno Insights.

Este frontend permite digitalizar el flujo de ingreso/salida de visitantes en una planta industrial mediante credenciales QR.

---

## Funcionalidades

### Login

- Login de guards mediante usuario + PIN
- Login especial de admin (`admin/admin`)
- Acceso directo al portal de visitantes

### Portal de Guard

Los guards pueden:

- Buscar visitantes por DNI
- Registrar nuevos visitantes
- Capturar foto desde cámara/webcam
- Generar credenciales QR
- Escanear QR para registrar ingreso/salida
- Visualizar credencial activa
- Ver dashboard de ingresos
- Descargar historial completo en Excel

### Dashboard

- Ingresos del día
- Total de visitantes registrados
- Historial reciente de visitas
- Exportación completa en `.xlsx`

### Portal de Visitante

Los visitantes pueden:

- Generar su propio QR de ingreso
- Consultar su credencial activa
- Seleccionar sector a visitar

### Panel Admin

Permite crear nuevos guards:

- username
- pin

---

# Tecnologías utilizadas

- React
- React Router DOM
- Axios
- html5-qrcode
- Vite

---

# Arquitectura Frontend

```bash
src/
 ├── api/
 ├── components/
 ├── hooks/
 ├── pages/
 ├── constants/
 └── styles/
```

### Hooks

- useLogin
- useCameraCapture
- useQrScanner
- useGuardForm
- useGuardDashboard
- useVisitorCredential

### API

- guardApi
- visitApi
- visitorApi
- adminApi

---

# Instalación local

```bash
git clone https://github.com/Santino2005/scolombo-Geno-Insights-Challenge.git
cd frontend
npm install
```

Crear archivo `.env`

```env
VITE_API_URL=http://localhost:8080
```

Ejecutar proyecto:

```bash
npm run dev
```

---

# Variables de entorno

```env
VITE_API_URL=
```

Ejemplo producción:

```env
VITE_API_URL=https://your-backend-url.up.railway.app
```

---

# Flujo principal

## Primer ingreso

1. Guard registra visitante
2. Captura foto
3. Genera credencial QR
4. Visitante recibe credencial

## Reingreso

1. Visitante muestra QR
2. Guard escanea QR
3. Sistema registra ingreso/salida automáticamente

---

# Mejoras implementadas respecto al sistema original

- Backend persistente
- Base de datos
- Almacenamiento de fotos en un bucket S3 o storage en la nube
- Generación real de QR
- Escaneo QR
- Dashboard
- Exportación Excel
- Roles (Admin / Guard / Visitor)

---

# Deploy

- Frontend: Vercel

---

# Credenciales de prueba

## Admin

```bash
admin
admin
```

Los guards se crean desde el panel admin.



**Preguntas antes de escalar a producción real**

¿Qué permisos debería tener cada rol?

¿El login por PIN alcanza o debería usarse autenticación con JWT, cookies seguras o un proveedor externo?

¿Qué pasa si un guardia deja la sesión abierta en una computadora compartida?

¿El QR debería representar una visita puntual o una credencial permanente del visitante?

¿Las fotos de visitantes deberían ser públicas, privadas o accesibles con URLs temporales?

¿Durante cuánto tiempo se deben conservar los datos personales y las fotos?

¿Qué política de privacidad aplica para el almacenamiento de imágenes y DNI?

¿Qué pasa si dos guardias intentan registrar al mismo visitante al mismo tiempo?

¿Debe soportar múltiples sedes, plantas o accesos físicos?

¿Cómo debería funcionar el sistema si internet se cae en la entrada?

¿Hace falta un modo offline o una planilla de contingencia sincronizable?

¿Qué reportes necesita la empresa además del Excel histórico?

¿El historial debería tener filtros por fecha, empresa, sector, guardia o estado de visita?

¿Qué métricas operativas serían útiles para el cliente?

¿Cómo se monitorea el backend y el frontend en producción?

---

# Autor

**Santino Colombo**

GitHub: https://github.com/Santino2005# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Oxc](https://oxc.rs)
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/)

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.
