export default function CameraPreview({ preview, videoRef }) {
    if (preview) {
        return (
            <img
                src={preview}
                alt="Foto capturada"
                className="camera"
            />
        );
    }

    return (
        <video
            ref={videoRef}
            autoPlay
            playsInline
            muted
            className="camera"
        />
    );
}