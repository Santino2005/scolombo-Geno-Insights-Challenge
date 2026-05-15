import CameraCapture from "../camera/CameraCapture";
import LastVisitorPhoto from "../LastVisitorPhoto";
import { sectors } from "../../constants/sectors";

export default function VisitorForm({
                                        form,
                                        visit,
                                        onUpdateField,
                                        onSearchVisitor,
                                        onRegisterVisitor,
                                        onCleanForm,
                                        onGenerateCredential,
                                        onOpenCredential,
                                        onOpenScanner,
                                    }) {
    return (
        <section className="card">
            <h2 className="card-title">Registro de Visitantes</h2>

            <div className="row">
                <input
                    placeholder="DNI"
                    value={form.dni}
                    onChange={(event) =>
                        onUpdateField("dni", event.target.value)
                    }
                />

                <button onClick={onSearchVisitor}>
                    🔍 Buscar
                </button>
            </div>

            <input
                placeholder="Nombre Completo"
                value={form.fullName}
                onChange={(event) =>
                    onUpdateField("fullName", event.target.value)
                }
            />

            <input
                placeholder="Empresa"
                value={form.company}
                onChange={(event) =>
                    onUpdateField("company", event.target.value)
                }
            />

            <select
                value={form.sector}
                onChange={(event) =>
                    onUpdateField("sector", event.target.value)
                }
            >
                {sectors.map((sector) => (
                    <option key={sector.value} value={sector.value}>
                        {sector.label}
                    </option>
                ))}
            </select>

            <div className="camera-label">
                📷 FOTO DEL VISITANTE
            </div>

            <CameraCapture
                onCapture={(photo) => onUpdateField("photo", photo)}
            />

            <LastVisitorPhoto
                photoUrl={form.photoUrl}
                hasNewPhoto={form.photo}
            />

            <div className="actions">
                <button className="primary" onClick={onRegisterVisitor}>
                    ✅ Registrar
                </button>

                <button onClick={onCleanForm}>
                    🧹 Limpiar
                </button>
            </div>

            <button className="wide-button" onClick={onGenerateCredential}>
                🪪 Generar credencial
            </button>

            {visit && (
                <button className="wide-button" onClick={onOpenCredential}>
                    👁 Ver credencial
                </button>
            )}

            <div className="divider" />

            <button className="wide-button" onClick={onOpenScanner}>
                📷 Escanear QR
            </button>
        </section>
    );
}