import { useQrScanner } from "../../hooks/useQrScanner";

const READER_ID = "qr-reader-modal";

export default function ScannerModal({ onClose }) {
    useQrScanner(READER_ID, onClose);

    return (
        <div className="modal-backdrop">
            <div className="modal-content scanner-modal">
                <section className="card">
                    <h2 className="card-title">Escanear QR</h2>

                    <div id={READER_ID} className="qr-reader-box" />

                    <button className="secondary" onClick={onClose}>
                        ✖ Cerrar
                    </button>
                </section>
            </div>
        </div>
    );
}