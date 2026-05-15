import { useEffect, useRef, useState } from "react";

export function useCameraCapture(onCapture) {
    const videoRef = useRef(null);
    const streamRef = useRef(null);

    const [started, setStarted] = useState(false);
    const [preview, setPreview] = useState(null);

    async function startCamera() {
        const stream = await navigator.mediaDevices.getUserMedia({
            video: true,
        });

        streamRef.current = stream;

        if (videoRef.current) {
            videoRef.current.srcObject = stream;
        }

        setStarted(true);
    }

    function stopCamera() {
        if (!streamRef.current) return;

        streamRef.current.getTracks().forEach(stopTrack);
        streamRef.current = null;
        setStarted(false);
    }

    function stopTrack(track) {
        track.stop();
    }

    function capturePhoto() {
        const canvas = createCanvasFromVideo();

        canvas.toBlob((blob) => {
            saveCapturedBlob(blob);
        }, "image/jpeg");
    }

    function createCanvasFromVideo() {
        const canvas = document.createElement("canvas");

        canvas.width = videoRef.current.videoWidth;
        canvas.height = videoRef.current.videoHeight;

        canvas.getContext("2d").drawImage(videoRef.current, 0, 0);

        return canvas;
    }

    function saveCapturedBlob(blob) {
        const file = new File([blob], "visitor-photo.jpg", {
            type: "image/jpeg",
        });

        setPreview(URL.createObjectURL(file));
        onCapture(file);
        stopCamera();
    }

    async function repeatPhoto() {
        setPreview(null);
        onCapture(null);
        await startCamera();
    }

    useEffect(() => {
        return () => {
            stopCamera();
        };
    }, []);

    return {
        videoRef,
        started,
        preview,
        startCamera,
        capturePhoto,
        repeatPhoto,
    };
}