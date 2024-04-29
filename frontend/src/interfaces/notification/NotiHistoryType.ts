interface NotiHistoryType {
  id: number;
  notificationType: "BUY" | "SELL" | "SETTLE" | "FORCE_SETTLE" | "FOLLOW";
  notificationDate: string;
  message: string;
  isRead: boolean;
}

export default NotiHistoryType;
