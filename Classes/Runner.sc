Runner {
	classvar scriptsDirectory;


	*initClass {
		scriptsDirectory = PathName(Platform.userConfigDir) +/+ PathName("setupScripts");
		File.mkdir(scriptsDirectory.fullPath);
	}

	*scriptsDirectory {
		^scriptsDirectory.fullPath;
	}

	*scripts {
		^scriptsDirectory.entries.collect(_.fileNameWithoutExtension);
	}

	*scriptPath {|scriptName|
		^scriptsDirectory +/+ PathName(scriptName ++ ".scd");
	}

	*saveScript {|scriptName, script|
		var scriptFile;
		scriptFile = File(this.scriptPath(scriptName).fullPath, "w");
		scriptFile.write(script.asCode);
		scriptFile.close;
	}

	*runScript {|scriptName, args|
		var script = (this.scriptPath(scriptName)).fullPath.load;
		case
		{args.isNil} {^script.value();}
		{args.isArray} {^script.valueArgsArray(args);}
		{args.class == Event} {^script.valueArgsDict(args);}
	}

	*runScriptDict {|scriptName, args|
		^(this.scriptPath(scriptName)).fullPath.load.valueArgsDict(args);
	}

	*openScript {|scriptName|
		Document.open(this.scriptPath(scriptName).fullPath);
	}

	*fetchScript {|scriptName|
		^File.readAllString(this.scriptPath(scriptName).fullPath);
	}

	*deleteScript {|scriptName|
		^File.delete(this.scriptPath(scriptName).fullPath);
	}
}