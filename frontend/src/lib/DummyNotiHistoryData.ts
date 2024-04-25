import NotiHistoryType from "@/interfaces/notification/NotiHistoryType";

const notiHistoryData: NotiHistoryType[] = [
  {
    id: 1,
    notificationType: "BUY",
    notificationDate: "2024-03-20T14:00:00",
    message: "[BTC/KRW] 0.0001781개가 99,580,000원에 매수 체결",
    isRead: false,
  },
  {
    id: 2,
    notificationType: "SELL",
    notificationDate: "2024-03-19T13:45:30",
    message: "주식 판매 알림입니다.",
    isRead: true,
  },
  {
    id: 3,
    notificationType: "SETTLE",
    notificationDate: "2024-03-18T17:20:05",
    message: "정산 완료 알림입니다.",
    isRead: false,
  },
  {
    id: 4,
    notificationType: "FORCE_SETTLE",
    notificationDate: "2024-03-17T09:15:45",
    message: "강제 정산 알림입니다.",
    isRead: true,
  },
  {
    id: 5,
    notificationType: "FOLLOW",
    notificationDate: "2024-03-16T16:05:10",
    message: "새로운 팔로우 알림입니다.",
    isRead: false,
  },
  {
    id: 6,
    notificationType: "BUY",
    notificationDate: "2024-03-15T11:30:20",
    message: "주식 구매 성공 알림입니다.",
    isRead: true,
  },
  {
    id: 7,
    notificationType: "SELL",
    notificationDate: "2024-03-14T12:50:00",
    message: "주식 판매 성공 알림입니다.",
    isRead: false,
  },
  {
    id: 8,
    notificationType: "SETTLE",
    notificationDate: "2024-03-13T10:40:30",
    message: "정산 알림입니다.",
    isRead: true,
  },
  {
    id: 9,
    notificationType: "FORCE_SETTLE",
    notificationDate: "2024-03-12T15:55:55",
    message: "강제 정산 처리 알림입니다.",
    isRead: false,
  },
  {
    id: 10,
    notificationType: "FOLLOW",
    notificationDate: "2024-03-11T18:00:05",
    message: "새 팔로워 알림입니다.",
    isRead: true,
  },
];

export default notiHistoryData;
