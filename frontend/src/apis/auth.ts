import { AxiosResponse } from "axios";
import { UserType } from "@/interfaces/user/UserType";
import localAxios from "@/utils/http-commons";

const version = "v1";
const domain = "auth";

async function postGoogleOAuth(
  success: (res: AxiosResponse<UserType>) => void,
  code: string | null,
) {
  localAxios
    .post(`/${version}/${domain}/google/signin?code=${code}`)
    .then(success);
}

export default postGoogleOAuth;
