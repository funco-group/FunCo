import { AxiosResponse } from "axios";
import MemberType from "@/interfaces/userPage/MemberType";
import localAxios from "@/utils/http-commons";

const version = "v1";
const domain = "members";

export async function getMemberInfo(
  memberId: number,
  success: (res: AxiosResponse<MemberType>) => void,
) {
  await localAxios.get(`/${version}/${domain}/${memberId}`).then(success);
}

export async function editNickname(nickname: string) {
  await localAxios.patch(`/${version}/${domain}/nickname`, {
    nickname: nickname,
  });
}

export async function editIntroduction(introduction: string) {
  await localAxios.patch(`/${version}/${domain}/introduction`, {
    introduction: introduction,
  });
}
