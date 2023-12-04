package domain.connectivity

sealed class ConnectionStatus {
  object Available : ConnectionStatus()
    object Unavailable: ConnectionStatus()
}