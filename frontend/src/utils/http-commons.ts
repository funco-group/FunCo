import axios from "axios";

const localAxios = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});

localAxios.interceptors.request.use(
  (config) => {
    const savedValue = localStorage.getItem("userInfo");
    const userInfo = savedValue ? JSON.parse(savedValue) : null;
    if (userInfo && userInfo.user !== null) {
      const newConfig = { ...config };
      newConfig.headers.Authorization = `Bearer ${userInfo.user.accessToken}`;
      return newConfig;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// localAxios.interceptors.response.use(
//     (response) => {
//         return response;
//     },
//     async (error) => {
//         if (error.response.status === httpStatusCode.UNAUTHORIZED) {
//             tokenRegenerate();
//             error.config.headers = {
//                 "Content-Type": "application/json",
//                 Authorization: `Bearer ${sessionStorage.getItem("accessToken")}`,
//             };
//             const response = await axios.request(error.config);
//             return response;
//         }
//         return Promise.reject(error);
//     }
// );

export default localAxios;
