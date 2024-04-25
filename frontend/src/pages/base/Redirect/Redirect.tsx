import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import postGoogleOAuth from "@/apis/auth";
import useUserState from "@/hooks/recoilHooks/useUserState";

function Redirect() {
  const queryParams = new URLSearchParams(window.location.search);
  const code = queryParams.get("code");
  const navigate = useNavigate();

  const { login } = useUserState();

  useEffect(() => {
    if (code) {
      postGoogleOAuth((res) => {
        const { data } = res;
        login(data); // Recoil 상태 업데이트
        navigate("/"); // 사용자를 홈 페이지로 리디렉트
      }, code);
    }
  }, [code, login, navigate]);

  return <div>redirect</div>;
}

export default Redirect;
