import { AxiosResponse } from "axios";
import NotiHistoryType from "@/interfaces/notification/NotiHistoryType";
import localAxios from "@/utils/http-commons";

const version = "v1";
const domain = "notifications";

export async function getNotiHistoryList(
  success: (res: AxiosResponse<NotiHistoryType[]>) => void,
) {
  await localAxios.get(`/${version}/${domain}?size=10`).then(success);
}

export async function sendReadNotiList(success: () => void) {
  await localAxios.patch(`/${version}/${domain}/read`).then(success);
}
