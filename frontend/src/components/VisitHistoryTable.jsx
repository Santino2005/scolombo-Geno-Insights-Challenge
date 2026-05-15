export default function VisitHistoryTable({ history }) {
    if (history.length === 0) {
        return (
            <div className="empty-history">
                No hay registros todavía
            </div>
        );
    }

    return (
        <table className="history-table">
            <thead>
            <tr>
                <th>FECHA</th>
                <th>INGRESO</th>
                <th>SALIDA</th>
                <th>DNI</th>
                <th>NOMBRE</th>
                <th>EMPRESA</th>
                <th>SECTOR</th>
                <th>ESTADO</th>
            </tr>
            </thead>

            <tbody>
            {history.slice(0, 8).map((item) => (
                <tr key={item.id || item.qrToken}>
                    <td>{formatDate(item.entryTime)}</td>
                    <td>{formatTime(item.entryTime)}</td>
                    <td>{formatTime(item.exitTime)}</td>
                    <td>{item.visitor?.dni || item.dni || "-"}</td>
                    <td>{item.visitor?.fullName || item.fullName || "-"}</td>
                    <td>{item.visitor?.company || item.company || "-"}</td>
                    <td>{item.sector || "-"}</td>
                    <td>
                            <span
                                className={
                                    item.exitTime
                                        ? "status-finished"
                                        : "status-active"
                                }
                            >
                                {item.exitTime ? "Finalizado" : "Activo"}
                            </span>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}

function formatDate(value) {
    if (!value) return "-";

    return new Date(value).toLocaleDateString();
}

function formatTime(value) {
    if (!value) return "-";

    return new Date(value).toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit",
    });
}