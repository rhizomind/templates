import { UserInfoDto } from './dtos';

/**
 * @pact
 * @pact-method GET
 * @pact-response-status 200
 * @pact-path /api/me
 */
export async function getUserInfo(): Promise<UserInfoDto> {
  return await api<UserInfoDto>('/api/me');
}

function api<T>(url: string): Promise<T> {
  return fetch(url).then(response => {
    if (!response.ok) {
      throw new Error(response.statusText);
    }
    let promise = response.json();
    return promise;
  });
}
