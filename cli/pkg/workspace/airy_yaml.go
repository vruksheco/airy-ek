package workspace

type KubernetesConf struct {
	Host string `yaml:"host"`
}

type IngressConf struct {
	NgrokEnabled            bool              `yaml:"ngrokEnabled"`
	Https                   bool              `yaml:"https,omitempty"`
	LetsencryptEmail        string            `yaml:"letsencryptEmail,omitempty"`
	LoadbalancerAnnotations map[string]string `yaml:"loadbalancerAnnotations,omitempty"`
}

type SecurityConf struct {
	SystemToken    string            `yaml:"systemToken,omitempty"`
	AllowedOrigins string            `yaml:"allowedOrigins"`
	JwtSecret      string            `yaml:"jwtSecret"`
	Oidc           map[string]string `yaml:"oidc,omitempty"`
}
type ComponentsConf map[string]map[string]string

type AiryConf struct {
	Kubernetes KubernetesConf            `yaml:"kubernetes"`
	Ingress    IngressConf               `yaml:"ingress"`
	Security   SecurityConf              `yaml:"security"`
	Components map[string]ComponentsConf `yaml:"components,omitempty"`
}

type HelmAiryConf struct {
	Global AiryConf `yaml:"global"`
}
