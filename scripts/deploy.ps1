# deploy.ps1

# 1. point to minikube daemon
minikube -p minikube docker-env --shell powershell | Invoke-Expression

# 2. build image
docker build -t kase-orchestrator:latest .

# 3. apply k8s manifests
kubectl apply -f ./k8s

# 4. restart deployment to pick up new image
kubectl rollout restart deployment/kase-orchestrator

# 5. watch pods
kubectl get pods -w