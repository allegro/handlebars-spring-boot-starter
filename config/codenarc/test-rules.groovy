ruleset {
    ruleset('file:config/codenarc/StarterRuleSet-AllRulesByCategory.groovy') {
        ClassJavadoc(enabled: false)
        MethodName(enabled: false)
        NoDef(enabled: false)
        SpaceAroundMapEntryColon(enabled: false)
        Instanceof(enabled: false)
    }
}
