import { sectors } from "../constants/sectors";

export default function VisitorCredentialForm({
                                                  dni,
                                                  sector,
                                                  loading,
                                                  onDniChange,
                                                  onSectorChange,
                                                  onGenerateCredential,
                                                  onViewCredential,
                                              }) {
    return (
        <section className="card">
            <h2 className="card-title">Portal del Visitante</h2>

            <label className="field-label">DNI</label>
            <input
                placeholder="Ej: 46742185"
                value={dni}
                onChange={onDniChange}
            />

            <label className="field-label">Sector</label>
            <select
                value={sector}
                onChange={onSectorChange}
            >
                {sectors.map((sector) => (
                    <option key={sector.value} value={sector.value}>
                        {sector.label}
                    </option>
                ))}
            </select>

            <button
                className="primary wide-button"
                onClick={onGenerateCredential}
                disabled={loading}
            >
                {loading ? "Generando..." : "🎟️ Generar QR de ingreso"}
            </button>

            <button
                className="wide-button"
                onClick={onViewCredential}
                disabled={loading}
            >
                {loading ? "Buscando..." : "🪪 Ver mi credencial activa"}
            </button>
        </section>
    );
}