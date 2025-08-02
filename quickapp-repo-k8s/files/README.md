# 📁 Static File Hosting with NGINX and Upload via rsync

This solution provides:

- A lightweight `nginx` container serving static files
- A companion `rsync` container allowing remote file uploads
- Shared storage volume between both containers
- Kubernetes ingress exposing files via:  
  `https://qa-repo.solidify.account.deploy-sphere.cloud`

---

## 🐳 Docker Compose Setup

This setup includes:
- An `nginx` container serving `/usr/share/nginx/html`
- An `rsync` container configured with basic authentication
- A shared named volume between the two

📄 See: [`docker-compose.yml`](./docker-compose.yml)

### Uploading Files via `rsync`

```bash
rsync -avz ./local-folder rsync://rsyncuser@localhost:18873/data
```

Replace `localhost` with the Docker host IP if needed. The default username/password are defined in the `docker-compose.yml` file.

---

## ☸️ Kubernetes Setup

This Kubernetes configuration deploys both `nginx` and `rsync` in a single pod with a shared `PersistentVolumeClaim`.

### Components:

- `persistent-volume-claim`: Defines shared storage (`ReadWriteMany`)
- `deployment`: Defines the pod with `nginx` and `rsync` sharing the volume
- `services`: Exposes ports 80 (nginx) and 873 (rsync)
- `ingress`: Exposes NGINX via external domain

### rsync Uploading

To upload files using `rsync` when deployed in Kubernetes, use port-forwarding:

```bash
kubectl port-forward svc/rsync-service 873:873
rsync -avz ./local-folder rsync://rsyncuser@localhost:873/data
```

---

## ✅ Accessing Uploaded Files

Once uploaded, the files are immediately accessible through:

```
https://qa-repo.solidify.account.deploy-sphere.cloud
```

(Assuming the DNS is correctly set up and points to the Ingress controller.)

---

## 🔐 Security Considerations

- Add HTTPS via `cert-manager` and Let's Encrypt
- Restrict `rsync` access using firewalls or port-forwarding only
- Consider adding Basic Auth or OAuth to NGINX if the repo should not be public

---

## 📌 Notes

- `ReadWriteMany` volume support is required in Kubernetes (e.g., NFS, AWS EFS, Longhorn)
- For production-grade deployments, ensure TLS is used
- Don't store secrets (like rsync passwords) directly in YAML files — consider Kubernetes Secrets or Sealed Secrets
