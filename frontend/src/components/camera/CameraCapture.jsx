import { useCameraCapture } from "../../hooks/useCameraCapture";
import CameraPreview from "./CameraPreview";
import CameraActions from "./CameraActions";

export default function CameraCapture({ onCapture }) {
    const camera = useCameraCapture(onCapture);

    return (
        <div className="camera-box">
            <CameraPreview
                preview={camera.preview}
                videoRef={camera.videoRef}
            />

            <CameraActions
                started={camera.started}
                preview={camera.preview}
                onStart={camera.startCamera}
                onCapture={camera.capturePhoto}
                onRepeat={camera.repeatPhoto}
            />
        </div>
    );
}