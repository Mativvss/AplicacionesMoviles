# Junta de Vecinos Población Zaror

Aplicación móvil Android para gestionar el arriendo del salón comunitario y dar transparencia a las finanzas de la Junta de Vecinos Población Zaror.

**Asignatura:** DSY1105 – Desarrollo de Aplicaciones Móviles


## Integrantes

- Matías Franke
- Fernanda Viveros

## Funcionalidades

**Reservas del salón**
- Formulario de solicitud con validación por campo: nombre, correo, teléfono, fecha (selector de calendario), horas de inicio y fin, monto, uso de cocina (Switch) y aceptación del reglamento (Checkbox).
- Mensajes de error e íconos de estado (válido o con error) en cada campo.
- Lista de reservas con confirmación y cancelación, y animación de color según el estado (pendiente, confirmada, cancelada).

**Finanzas**
- Registro de movimientos (ingresos y gastos) con tipo, categoría, descripción y monto.
- Fotografía del comprobante con la cámara del teléfono.
- Lista de movimientos con miniatura del comprobante.

**General**
- Persistencia local de los datos con Room.
- Navegación inferior entre Nueva reserva, Reservas y Finanzas.

## Recursos nativos

1. **Cámara**: permite fotografiar el comprobante de un movimiento. Solicita el permiso en tiempo de ejecución y guarda la imagen mediante FileProvider.
2. **Notificaciones locales**: al registrar una reserva, la app muestra una notificación. Usa un canal de notificación y solicita el permiso `POST_NOTIFICATIONS` en Android 13 o superior.

## Tecnologías

Kotlin, Jetpack Compose, Material 3, arquitectura MVVM, StateFlow, Room (con KSP) y Coil.

## Estructura del proyecto

- `data/`: entidades, DAO, repositorios y base de datos Room.
- `model/`: estado y errores de los formularios.
- `domain/`: reglas de validación.
- `viewmodel/`: lógica y estado de cada pantalla.
- `ui/`: pantallas, componentes reutilizables y navegación.
- `util/`: formato de montos y fechas, y notificaciones.
