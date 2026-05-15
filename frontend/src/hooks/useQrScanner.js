import { useEffect, useRef } from "react";
import { Html5QrcodeScanner } from "html5-qrcode";
import { scanVisitQr } from "../api/visitApi";

export function useQrScanner(readerId, onClose) {
    const scannedRef = useRef(false);

    useEffect(() => {
        const scanner = createScanner(readerId);

        scanner.render((decodedText) => {
            handleScan(decodedText, scanner);
        });

        return () => {
            scanner.clear().catch(() => {});
        };
    }, [readerId, onClose]);

    function createScanner(readerId) {
        return new Html5QrcodeScanner(
            readerId,
            {
                fps: 10,
                qrbox: {
                    width: 260,
                    height: 260,
                },
                rememberLastUsedCamera: true,
                showTorchButtonIfSupported: true,
            },
            false
        );
    }

    async function handleScan(decodedText, scanner) {
        if (scannedRef.current) return;

        scannedRef.current = true;

        try {
            const response = await scanVisitQr(decodedText);
            showScanMessage(response.data.status);

            await scanner.clear();
            onClose();
        } catch {
            alert("QR inválido o visita ya cerrada");
            scannedRef.current = false;
        }
    }

    function showScanMessage(status) {
        if (status === "ACTIVE") {
            alert("Ingreso registrado");
        }

        if (status === "ENDED") {
            alert("Salida registrada");
        }
    }
}