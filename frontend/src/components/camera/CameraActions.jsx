export default function CameraActions({
                                          started,
                                          preview,
                                          onStart,
                                          onCapture,
                                          onRepeat,
                                      }) {
    if (!started && !preview) {
        return (
            <button onClick={onStart}>
                📷 Activar cámara
            </button>
        );
    }

    if (started && !preview) {
        return (
            <button onClick={onCapture}>
                📸 Capturar foto
            </button>
        );
    }

    return (
        <button onClick={onRepeat}>
            🔁 Repetir foto
        </button>
    );
}