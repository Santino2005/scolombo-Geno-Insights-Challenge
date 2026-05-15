export default function LoginForm({
                                      username,
                                      pin,
                                      onUsernameChange,
                                      onPinChange,
                                      onSubmit,
                                  }) {
    return (
        <form onSubmit={onSubmit}>
            <input
                placeholder="Usuario"
                value={username}
                onChange={onUsernameChange}
            />

            <input
                placeholder="PIN"
                type="password"
                value={pin}
                onChange={onPinChange}
            />

            <button type="submit">
                Ingresar como Guard
            </button>
        </form>
    );
}